import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AceEditor from "react-ace";
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/theme-github";
import { boilerplates } from '../boilerplates';
import { useAuth } from '../contexts/AuthContext';
import { useLocation } from 'react-router-dom';
import './CodeEditor.css';

const CodeEditor = ({ darkMode, toggleDarkMode }) => {
    const location = useLocation();
    const [code, setCode] = useState('// Write your code here');
    const [output, setOutput] = useState('');
    const [language, setLanguage] = useState('c');
    const [loading, setLoading] = useState(false);
    const [projectName, setProjectName] = useState('');
    const { currentUser } = useAuth();

    const languageMap = {
        java: 62,
        c: 50,
        cpp: 54,
        python: 71,
        javascript: 63
    };

    useEffect(() => {
        setCode(boilerplates[language]);
    }, [language]);

    useEffect(() => {
        if (currentUser) {
            const unsavedProject = JSON.parse(window.localStorage.getItem('unsavedProject'));
            if (unsavedProject) {
                setProjectName(unsavedProject.projectName);
                setCode(unsavedProject.code);
                setLanguage(unsavedProject.language);
            } else {
                setCode(boilerplates[language]);
            }
        }
    }, [currentUser, language]);

    useEffect(() => {
        const fetchProjectData = async (projectId) => {
            try {
                const response = await axios.get(`http://localhost:5000/api/projects/${projectId}`);
                const { projectName, code, language } = response.data;
                setProjectName(projectName);
                setCode(code);
                setLanguage(language);
            } catch (error) {
                console.error('Error fetching project data:', error);
            }
        };

        if (location.state && location.state.projectId) {
            fetchProjectData(location.state.projectId);
        }
    }, [location.state]);

    useEffect(() => {
        if (!currentUser) {
            setProjectName('');
            setCode(boilerplates['c']);
            setLanguage('c');
            window.localStorage.removeItem('unsavedProject');
        }
    }, [currentUser]);

    const decodeBase64 = (str) => {
        return decodeURIComponent(atob(str).split('').map((c) => {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
    };

    const runCode = async () => {
    setOutput('Running...');
    setLoading(true);
    const encodedParams = new URLSearchParams();
    encodedParams.append("source_code", btoa(code));
    encodedParams.append("language_id", languageMap[language]);

    try {
        // Step 1: Submit code for execution
        const response = await axios.post('https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=true', encodedParams, {
            headers: {
                'content-type': 'application/x-www-form-urlencoded',
                'X-RapidAPI-Key': '70ea03f8f7msh4e658c5f952860cp1e6e56jsnd53635e66874',
                'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
            }
        });

        const token = response.data.token;

        // Step 2: Poll Judge0 API until the program finishes execution
        let result = await axios.get(`https://judge0-ce.p.rapidapi.com/submissions/${token}?base64_encoded=true`, {
            headers: {
                'X-RapidAPI-Key': '70ea03f8f7msh4e658c5f952860cp1e6e56jsnd53635e66874',
                'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
            }
        });

        while (result.data.status.id <= 2) {
            await new Promise((resolve) => setTimeout(resolve, 1000));
            result = await axios.get(`https://judge0-ce.p.rapidapi.com/submissions/${token}?base64_encoded=true`, {
                headers: {
                    'X-RapidAPI-Key': '70ea03f8f7msh4e658c5f952860cp1e6e56jsnd53635e66874',
                    'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
                }
            });

            // Step 3: Handle input request during execution
            if (result.data.status.id === 1 && result.data.stdout && result.data.stdout.includes("Enter a number:")) {
                const userInput = prompt('Enter input:');
                if (userInput !== null) {
                    const inputBase64 = btoa(userInput);
                    await axios.post(`https://judge0-ce.p.rapidapi.com/submissions/${token}/inject?base64_encoded=true`, {
                        source_code: inputBase64
                    }, {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded',
                            'X-RapidAPI-Key': '70ea03f8f7msh4e658c5f952860cp1e6e56jsnd53635e66874',
                            'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
                        }
                    });
                } else {
                    setOutput('Execution interrupted by user.');
                    return;
                }
            }
        }

        // Step 4: Process and display output
        const decodedOutput = result.data.stdout ? decodeBase64(result.data.stdout) : '';
        const decodedError = result.data.stderr ? decodeBase64(result.data.stderr) : '';
        const decodedCompileOutput = result.data.compile_output ? decodeBase64(result.data.compile_output) : '';

        setOutput(decodedOutput || decodedError || decodedCompileOutput || 'No output returned');
    } catch (error) {
        console.error('Error running code:', error);
        if (error.response) {
            const errorMsg = error.response.data.message || JSON.stringify(error.response.data);
            setOutput(`Error running code: ${errorMsg}`);
        } else {
            setOutput(`Error running code: ${error.message}`);
        }
    } finally {
        setLoading(false);
    }
};

    const handleSaveProject = () => {
        const projectNameInput = prompt('Enter project name:');
        if (projectNameInput !== null) {
            setProjectName(projectNameInput.trim());
            saveProject(projectNameInput.trim());
        }
    };

    const saveProject = async (projectNameInput) => {
    if (!projectNameInput) {
        alert('Please enter a project name');
        return;
    }

    if (!currentUser) {
        alert('You need to be signed in to save a project.');
        window.localStorage.setItem('unsavedProject', JSON.stringify({ projectName: projectNameInput, code, language }));
        window.location.href = '/signin';
        return;
    }

    try {
        // Check if project name already exists for the current user
        const existingProjects = await axios.get(`http://localhost:5000/api/myprojects?email=${currentUser.email}`);
        const projectExists = existingProjects.data.some(project => project.projectName === projectNameInput);

        if (projectExists) {
            const confirmReplace = window.confirm('File already exists. Do you want to replace it?');
            if (!confirmReplace) {
                return; // User chose not to replace, so do not save
            }
        }

        // Proceed with saving the project
        const response = await axios.post('http://localhost:5000/api/saveProject', {
            projectName: projectNameInput,
            code,
            language,
            email: currentUser.email
        });

        if (response.data && response.data.message) {
            alert(response.data.message);

            // Remove existing project files if they exist
            if (projectExists) {
                await axios.delete(`http://localhost:5000/api/projects/${existingProjects.data.find(project => project.projectName === projectNameInput)._id}`);
            }

            setProjectName('');
            setCode(boilerplates[language]);
            setLanguage('c');
            window.localStorage.removeItem('unsavedProject');
        } else {
            alert('Project saved successfully');
        }
    } catch (error) {
        console.error('Error saving project:', error);
        //alert('Failed to save project');
    }
};



    const handleNewEditor = () => {
        setCode(boilerplates[language]);
        setOutput('');
        setLanguage('c');
        setProjectName('');
        window.localStorage.removeItem('unsavedProject');
    };

    return (
        <div className={`editor-container ${darkMode ? 'dark-mode' : ''}`}>
            <div className="header">
                <h2>{projectName || 'Code Editor'}</h2>
            </div>
            <div className="toggle-section">
                <div className="toggle-switch">
                    <label className="switch">
                        <input type="checkbox" checked={darkMode} onChange={toggleDarkMode} />
                        <span className="slider round"></span>
                    </label>
                    <span>Dark Mode</span>
                </div>
                <div className="language-select">
                    <label htmlFor="language-select">Language: </label>
                    <select
                        id="language-select"
                        value={language}
                        onChange={(e) => setLanguage(e.target.value)}
                    >
                        <option value="java">Java</option>
                        <option value="c">C</option>
                        <option value="cpp">C++</option>
                        <option value="python">Python</option>
                        <option value="javascript">JavaScript</option>
                    </select>
                </div>
            </div>
            <div className="editor-output">
                <AceEditor
                    mode="c_cpp"
                    theme={darkMode ? "monokai" : "github"}
                    onChange={(newCode) => setCode(newCode)}
                    value={code}
                    width="50%"
                    height="400px"
                    fontSize={16}
                    fontFamily="Courier New, monospace"
                    className="code-editor"
                />
                <div className={`output-container ${darkMode ? 'dark-mode-output' : ''}`}>
                    <h3>Output:</h3>
                    <div className="output">
                        {loading ? (
                            <div className="spinner-container">
                                <div className="spinner"></div>
                                <span>Running...</span>
                            </div>
                        ) : (
                            <pre>{output}</pre>
                        )}
                    </div>
                </div>
            </div>
            <div className="project-save-section">
                <button className="save-button" onClick={handleSaveProject}>Save Project</button>
            </div>
           
            <div className="button-section">
                <button className="new-editor-button" onClick={handleNewEditor}>New Editor</button>
            </div>
            <button className="run-button" onClick={runCode}>Run</button>
        </div>
    );
};

export default CodeEditor;
