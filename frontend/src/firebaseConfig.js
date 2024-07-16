// src/firebaseConfig.js
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
	apiKey: "AIzaSyArrDWQ3BOL2dgp1TeEKDWP9mEBd3VlvkY",
	authDomain: "online-ide-and-code-compiler.firebaseapp.com",
	projectId: "online-ide-and-code-compiler",
	storageBucket: "online-ide-and-code-compiler.appspot.com",
	messagingSenderId: "1608352675",
	appId: "1:1608352675:web:e666205048a18f6ff39016"
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

export { auth };
