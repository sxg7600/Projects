import React, { useState } from 'react';
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from '../firebaseConfig';
import './SignIn.css';
import { useAuth } from '../contexts/AuthContext';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom

const SignIn = ({ darkMode }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { saveUnsavedProject, clearUnsavedProject } = useAuth();

    const handleSignIn = async (e) => {
        e.preventDefault();

        if (!email || !password) {
            setError("Please enter all fields.");
            return;
        }

        setLoading(true);

        try {
            // Save unsaved project to localStorage before signing in
            saveUnsavedProject({
                projectName: localStorage.getItem('projectName'),
                code: localStorage.getItem('code'),
                language: localStorage.getItem('language')
            });

            await signInWithEmailAndPassword(auth, email, password);

            // Clear unsaved project from localStorage after successful sign-in
            clearUnsavedProject();

            window.location.href = '/'; // Redirect to main home page
        } catch (err) {
            if (err.code === 'auth/wrong-password') {
                setError("Incorrect password. Please try again.");
            } else if (err.code === 'auth/user-not-found') {
                setError("Email not registered. Please Sign Up.");
            } else {
                setError("Sign in failed. Please try again later.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={`signin-container ${darkMode ? 'dark-mode' : ''}`}>
            <h2>Sign In</h2>
            <form onSubmit={handleSignIn}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
                <button type="submit" disabled={loading} className={`signin-button ${darkMode ? 'dark-mode' : ''}`}>Sign In</button>
            </form>
            {error && <p className={`error ${darkMode ? 'dark-mode' : ''}`}>{error}</p>}
            {loading && (
                <div className="loading-container">
                    <div className="loading-spinner"></div>
                </div>
            )}
            <p className="signup-link">
                Do not have your account? <Link to="/signup" className={`signup-link-text ${darkMode ? 'dark-mode' : ''}`}>Sign Up</Link>
            </p>
        </div>
    );
};

export default SignIn;
