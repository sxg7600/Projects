import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import CodeEditor from './components/CodeEditor';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import MyProjects from './components/MyProjects';
import { useAuth, AuthProvider } from './contexts/AuthContext';
import './App.css';

function App() {
    const [darkMode, setDarkMode] = useState(() => {
        const savedMode = localStorage.getItem('darkMode');
        return savedMode ? JSON.parse(savedMode) : false;
    });

    const toggleDarkMode = () => {
        const newMode = !darkMode;
        setDarkMode(newMode);
        localStorage.setItem('darkMode', JSON.stringify(newMode));
    };

    return (
        <AuthProvider>
            <Router>
                <div className={`App ${darkMode ? 'dark-mode' : ''}`}>
                    <header className="App-header">
                        <div className="header-left">
                            <h1 className="App-title">Online Code Compiler</h1>
                        </div>
                        <Navigation />
                    </header>
                    <main className="App-main">
                        <Routes>
                            <Route path="/" element={<CodeEditor darkMode={darkMode} toggleDarkMode={toggleDarkMode} />} />
                            <Route path="/signin" element={<SignIn darkMode={darkMode} />} />
                            <Route path="/signup" element={<SignUp darkMode={darkMode} />} />
                            <Route path="/myprojects" element={<MyProjects darkMode={darkMode} />} />
                        </Routes>
                    </main>
                    <footer className="App-footer">
                        <p>&copy; 2024 Online Code Compiler. All rights reserved.</p>
                    </footer>
                </div>
            </Router>
        </AuthProvider>
    );
}

function Navigation() {
    const { currentUser, logout } = useAuth();

    const handleLogout = async () => {
        try {
            await logout();
            // No need for window.location.href redirection

        } catch (err) {
            console.error("Failed to log out", err);
        }
    };

    return (
        <div className="header-right">
            <nav className="header-links">
                <UserInfo />
                <Link to="/" className="nav-link">Home</Link>
                <Link to="/myprojects" className="nav-link">My Projects</Link>
                {currentUser ? (
                    <>
                        <button onClick={handleLogout} className="nav-link logout-button">Log Out</button>
                    </>
                ) : (
                    <>
                        <Link to="/signin" className="nav-link">Sign In</Link>
                        <Link to="/signup" className="nav-link">Sign Up</Link>
                    </>
                )}
            </nav>
        </div>
    );
}

function UserInfo() {
    const { currentUser } = useAuth();

    return currentUser ? (
        <div className="user-info">
            {currentUser.displayName.toUpperCase()}
        </div>
    ) : null;
}

export default App;
