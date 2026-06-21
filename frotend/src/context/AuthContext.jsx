import React, { createContext, useContext, useState, useEffect } from 'react';
import authService from '../services/authService';
import { decodeToken, isTokenExpired } from '../utils/jwtUtils';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Initialize auth state from localstorage
  useEffect(() => {
    const initAuth = () => {
      const token = localStorage.getItem('token');
      const savedUser = localStorage.getItem('user');

      if (token && !isTokenExpired(token)) {
        if (savedUser) {
          setUser(JSON.parse(savedUser));
        } else {
          // Fallback to decoding token details
          const decoded = decodeToken(token);
          setUser({
            email: decoded.sub,
            role: decoded.role || decoded.roles?.[0] || 'STUDENT',
            name: decoded.name || 'User',
          });
        }
      } else {
        // Clear expired session
        localStorage.removeItem('token');
        localStorage.removeItem('user');
      }
      setLoading(false);
    };

    initAuth();
  }, []);

  // Set up global 401 listener to auto logout
  useEffect(() => {
    const handleUnauthorized = () => {
      logout();
    };
    window.addEventListener('auth-unauthorized', handleUnauthorized);
    return () => {
      window.removeEventListener('auth-unauthorized', handleUnauthorized);
    };
  }, []);

  const login = async (email, password, rememberMe) => {
    setLoading(true);
    try {
      const data = await authService.login(email, password);
      // Expected backend response: { token: '...', user: { email, name, role } }
      const token = data.token;
      const decoded = decodeToken(token);
      
      const loggedUser = data.user || {
        email: decoded.sub || email,
        role: decoded.role || decoded.roles?.[0] || 'STUDENT',
        name: decoded.name || 'User',
      };

      if (rememberMe) {
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(loggedUser));
      } else {
        sessionStorage.setItem('token', token);
        sessionStorage.setItem('user', JSON.stringify(loggedUser));
        localStorage.setItem('token', token); // Still save to localStorage for simplified Axios config, clean up on close or session end
        localStorage.setItem('user', JSON.stringify(loggedUser));
      }

      setUser(loggedUser);
      return loggedUser;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    authService.logout();
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    setUser(null);
  };

  const registerStudent = async (studentData) => {
    return await authService.registerStudent(studentData);
  };

  const registerRecruiter = async (recruiterData) => {
    return await authService.registerRecruiter(recruiterData);
  };

  const forgotPassword = async (email) => {
    return await authService.forgotPassword(email);
  };

  const resetPassword = async (token, password) => {
    return await authService.resetPassword(token, password);
  };

  const value = {
    user,
    loading,
    login,
    logout,
    registerStudent,
    registerRecruiter,
    forgotPassword,
    resetPassword,
    isAuthenticated: !!user,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
