import React, { createContext, useState, useEffect } from "react";
import {jwtDecode} from "jwt-decode";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authState, setAuthState] = useState({
    token: null,
    user: null,
  });

  useEffect(() => {
    const token = localStorage.getItem("token");
    const user = localStorage.getItem("user");

    if (token && user) {
      setAuthState({
        token,
        user: JSON.parse(user),
      });
    }
  }, []);

  const login = (token) => {
    const decoded = jwtDecode(token);
    console.log("Decoded user:", decoded);
    setAuthState({
      token,
      user: decoded,
    });
    localStorage.setItem("token", token);
    localStorage.setItem("user", JSON.stringify(decoded));
  };

  const logout = () => {
    setAuthState({
      token: null,
      user: null,
    });
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  };

  return (
    <AuthContext.Provider
      value={{
        token: authState.token,
        user: authState.user,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
