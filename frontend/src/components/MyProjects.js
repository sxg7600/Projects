import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import axios from 'axios';
import deleteIcon from './delete.png'; // Import delete.png
import './MyProjects.css'; // Import CSS for MyProjects

const MyProjects = ({ darkMode, toggleDarkMode }) => {
    const { currentUser } = useAuth();
    const navigate = useNavigate();
    const [projectNames, setProjectNames] = useState([]);
    const [loading, setLoading] = useState(false);

    // Define fetchProjectNames inside a useCallback to memoize it
    const fetchProjectNames = useCallback(async () => {
        setLoading(true);
        try {
            const response = await axios.get('http://localhost:5000/api/myprojects', {
                params: { email: currentUser.email }
            });
            setProjectNames(response.data);
        } catch (error) {
            console.error('Error fetching project names:', error);
        } finally {
            setLoading(false);
        }
    }, [currentUser]);

    useEffect(() => {
        if (!currentUser) {
            navigate('/signin');
        } else {
            fetchProjectNames(); // Call fetchProjectNames here
        }
    }, [currentUser, navigate, fetchProjectNames]);

    const openProject = (projectId) => {
        navigate('/', { state: { projectId } });
    };

    const deleteProject = async (projectId, projectName) => {
        const confirmDelete = window.confirm(
            `WARNING\n\nThis action will lead to permanent deletion of your project '${projectName}'.\n\nAre you sure you want to delete?`
        );

        if (confirmDelete) {
            try {
                await axios.delete(`http://localhost:5000/api/projects/${projectId}`);
                // Update state to reflect deletion
                setProjectNames(projectNames.filter(project => project._id !== projectId));
                // Show alert upon successful deletion
                window.alert(`'${projectName}' deleted successfully!`);
            } catch (error) {
                console.error('Error deleting project:', error);
            }
        }
    };

    if (!currentUser) {
        return (
            <div className="my-projects-container">
                <h2>You are not signed in</h2>
                <button onClick={() => navigate('/signin')} className="sign-in-button">
                    Sign in to continue
                </button>
            </div>
        );
    }

    return (
        <div className={`my-projects-container ${darkMode ? 'dark-mode' : ''}`}>
            <h2>My Projects</h2>
            {loading ? (
                <p>Loading projects...</p>
            ) : (
                <ul>
                    {projectNames.map(project => (
                        <li key={project._id} className={darkMode ? 'dark-mode' : ''}>
                            <div className="project-info" onClick={() => openProject(project._id)}>
                                <h3>{project.projectName}</h3>
                            </div>
                            <img
                                src={deleteIcon}
                                alt="Delete"
                                className={`delete-icon ${darkMode ? 'dark-mode' : ''}`}
                                onClick={() => deleteProject(project._id, project.projectName)}
                            />
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MyProjects;
