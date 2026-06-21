import React, { createContext, useState, useEffect, useContext } from 'react';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';

const ThemeContext = createContext();

export const useThemeContext = () => useContext(ThemeContext);

export const ThemeContextProvider = ({ children }) => {
  const [darkMode, setDarkMode] = useState(() => {
    const saved = localStorage.getItem('themeMode');
    return saved === 'dark';
  });

  useEffect(() => {
    localStorage.setItem('themeMode', darkMode ? 'dark' : 'light');
  }, [darkMode]);

  const toggleTheme = () => setDarkMode((prev) => !prev);

  // Custom typography and palette matching a premium UI
  const theme = createTheme({
    palette: {
      mode: darkMode ? 'dark' : 'light',
      primary: {
        main: darkMode ? '#60A5FA' : '#1E3A8A', // Blue
        light: darkMode ? '#93C5FD' : '#3B82F6',
        dark: darkMode ? '#2563EB' : '#172554',
      },
      secondary: {
        main: darkMode ? '#2DD4BF' : '#0D9488', // Teal
      },
      background: {
        default: darkMode ? '#0F172A' : '#F8FAFC', // Slate theme colors
        paper: darkMode ? '#1E293B' : '#FFFFFF',
      },
      text: {
        primary: darkMode ? '#F8FAFC' : '#0F172A',
        secondary: darkMode ? '#94A3B8' : '#475569',
      },
    },
    typography: {
      fontFamily: '"Inter", "Outfit", "Roboto", "Helvetica", "Arial", sans-serif',
      h1: { fontWeight: 700 },
      h2: { fontWeight: 700 },
      h3: { fontWeight: 600 },
      h4: { fontWeight: 600 },
      h5: { fontWeight: 600 },
      h6: { fontWeight: 600 },
      button: { textTransform: 'none', fontWeight: 600 },
    },
    components: {
      MuiButton: {
        styleOverrides: {
          root: {
            borderRadius: '8px',
            padding: '8px 16px',
            transition: 'all 0.2s ease-in-out',
            '&:hover': {
              transform: 'translateY(-1px)',
              boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
            },
          },
        },
      },
      MuiCard: {
        styleOverrides: {
          root: {
            borderRadius: '12px',
            boxShadow: darkMode 
              ? '0 4px 20px 0 rgba(0, 0, 0, 0.4)'
              : '0 4px 20px 0 rgba(148, 163, 184, 0.15)',
            border: darkMode ? '1px solid rgba(255, 255, 255, 0.05)' : '1px solid rgba(226, 232, 240, 0.8)',
            transition: 'all 0.3s ease',
          },
        },
      },
      MuiAppBar: {
        styleOverrides: {
          root: {
            backdropFilter: 'blur(8px)',
            backgroundColor: darkMode ? 'rgba(30, 41, 59, 0.8)' : 'rgba(255, 255, 255, 0.8)',
            color: darkMode ? '#F8FAFC' : '#0F172A',
            borderBottom: darkMode ? '1px solid rgba(255, 255, 255, 0.05)' : '1px solid rgba(226, 232, 240, 0.8)',
          },
        },
      },
      MuiDrawer: {
        styleOverrides: {
          paper: {
            backgroundColor: darkMode ? '#0F172A' : '#1E293B',
            color: '#FFFFFF',
          },
        },
      },
    },
  });

  return (
    <ThemeContext.Provider value={{ darkMode, toggleTheme }}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </ThemeContext.Provider>
  );
};
