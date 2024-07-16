// src/contexts/AuthContext.js

import React, { createContext, useContext, useState, useEffect } from 'react';
import { auth } from '../firebaseConfig';
import { onAuthStateChanged, signOut } from "firebase/auth";

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (user) => {
            setCurrentUser(user);
            setLoading(false);
        });

        return unsubscribe;
    }, []);

    const saveUnsavedProject = ({ projectName, code, language }) => {
        localStorage.setItem('projectName', projectName);
        localStorage.setItem('code', code);
        localStorage.setItem('language', language);
    };

    const clearUnsavedProject = () => {
        localStorage.removeItem('projectName');
        localStorage.removeItem('code');
        localStorage.removeItem('language');
    };

    const logout = () => {
        return signOut(auth);
    };

    const value = {
        currentUser,
        saveUnsavedProject,
        clearUnsavedProject,
        logout,
    };

    return (
        <AuthContext.Provider value={value}>
            {!loading && children}
        </AuthContext.Provider>
    );
}
