// src/components/Navigation.js
import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import './Navigation.css';

const Navigation = () => {
    const { currentUser, logout } = useAuth();

    const handleLogout = async () => {
        try {
            await logout();
            window.location.href = '/'; // Redirect to home page
        } catch (err) {
            console.error("Failed to log out", err);
        }
    };

    return (
        <nav className="navigation">
            <Link to="/">Home</Link>
            {currentUser ? (
                <>
                    <button onClick={handleLogout}>Log Out</button>
                </>
            ) : (
                <>
                    <Link to="/signin">Sign In</Link>
                    <Link to="/signup">Sign Up</Link>
                </>
            )}
        </nav>
    );
};

export default Navigation;
