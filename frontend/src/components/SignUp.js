import React, { useState } from 'react';
import { createUserWithEmailAndPassword, updateProfile } from "firebase/auth";
import { auth } from '../firebaseConfig';
import './SignUp.css'; // Import your CSS file for SignUp
import { Link } from 'react-router-dom'; // Import Link from react-router-dom

const SignUp = ({ darkMode }) => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dob, setDob] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [retypePassword, setRetypePassword] = useState('');
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSignUp = async (e) => {
        e.preventDefault();

        if (!firstName || !lastName || !dob || !email || !password || !retypePassword) {
            setError("Please fill all the fields.");
            return;
        }

        if (password !== retypePassword) {
            setError("Passwords do not match");
            return;
        }

        setLoading(true);

        try {
            const userCredential = await createUserWithEmailAndPassword(auth, email, password);
            const user = userCredential.user;
            await updateProfile(user, { displayName: `${firstName} ${lastName}` });
            setSuccessMessage("Sign up successful! Redirecting to Sign In page...");
            setTimeout(() => {
                window.location.href = '/signin';
            }, 2000);
        } catch (err) {
            if (err.code === 'auth/email-already-in-use') {
                setError("Email already in use. Please use a different email.");
            } else if (err.code === 'auth/invalid-email') {
                setError("Invalid email format. Please check your email.");
            } else if (err.code === 'auth/weak-password') {
                setError("Weak password. Please use a stronger password.");
            } else {
                setError("Sign up failed. Please try again later.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={`signup-container ${darkMode ? 'dark-mode' : ''}`}>
            <h2>Sign Up</h2>
            <form onSubmit={handleSignUp}>
                <input
                    type="text"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
                <input
                    type="text"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
                <input
                    type="date"
                    placeholder="Date of Birth"
                    value={dob}
                    onChange={(e) => setDob(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
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
                <input
                    type="password"
                    placeholder="Retype Password"
                    value={retypePassword}
                    onChange={(e) => setRetypePassword(e.target.value)}
                    className={`input-field ${darkMode ? 'dark-mode' : ''}`}
                />
                <button type="submit" disabled={loading} className={`signup-button ${darkMode ? 'dark-mode' : ''}`}>Sign Up</button>
            </form>
            {error && <p className={`error ${darkMode ? 'dark-mode' : ''}`}>{error}</p>}
            {successMessage && <p className={`success ${darkMode ? 'dark-mode' : ''}`}>{successMessage}</p>}
            {loading && <div className="loading-spinner"></div>}
          
			<p className="signup-link">
                <span className={`signup-text ${darkMode ? 'dark-mode' : ''}`}>Already have your account? </span>
                <Link to="/signin" className={`signup-link-text ${darkMode ? 'dark-mode' : ''}`}>Sign In</Link>
            </p>
        </div>
    );
};

export default SignUp;
