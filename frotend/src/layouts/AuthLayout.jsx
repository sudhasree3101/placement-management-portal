import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { Box, Container, Card, CardContent, Typography, IconButton } from '@mui/material';
import { Brightness4 as DarkIcon, Brightness7 as LightIcon } from '@mui/icons-material';
import { useThemeContext } from '../context/ThemeContext';

const AuthLayout = () => {
  const { darkMode, toggleTheme } = useThemeContext();
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        position: 'relative',
        py: 6,
        px: 2,
        background: (theme) =>
          theme.palette.mode === 'dark'
            ? 'radial-gradient(circle at 10% 20%, rgba(30, 41, 59, 1) 0%, rgba(15, 23, 42, 1) 90%)'
            : 'radial-gradient(circle at 10% 20%, rgba(241, 245, 249, 1) 0%, rgba(226, 232, 240, 1) 90%)',
      }}
    >
      {/* Top action buttons */}
      <Box sx={{ position: 'absolute', top: 16, right: 16, display: 'flex', gap: 1 }}>
        <IconButton onClick={toggleTheme} color="inherit">
          {darkMode ? <LightIcon /> : <DarkIcon />}
        </IconButton>
      </Box>

      <Container maxWidth="sm">
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mb: 3 }}>
          <Typography
            variant="h4"
            component="h1"
            sx={{
              fontWeight: 800,
              cursor: 'pointer',
              mb: 1,
              letterSpacing: 1.5,
              background: 'linear-gradient(90deg, #3B82F6 0%, #10B981 100%)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              display: 'flex',
              alignItems: 'center',
              gap: 1.5
            }}
            onClick={() => navigate('/')}
          >
            <span>🎯</span> CareerGate
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ fontWeight: 500 }}>
            Unified College Placement & Career Tracking Portal
          </Typography>
        </Box>

        <Card
          sx={{
            width: '100%',
            borderRadius: '16px',
            backdropFilter: 'blur(20px)',
            backgroundColor: (theme) =>
              theme.palette.mode === 'dark' ? 'rgba(30, 41, 59, 0.7)' : 'rgba(255, 255, 255, 0.7)',
            boxShadow: (theme) =>
              theme.palette.mode === 'dark'
                ? '0 20px 40px -15px rgba(0,0,0,0.7)'
                : '0 20px 40px -15px rgba(148, 163, 184, 0.3)',
          }}
        >
          <CardContent sx={{ p: { xs: 3, md: 5 } }}>
            <Outlet />
          </CardContent>
        </Card>
      </Container>
    </Box>
  );
};

export default AuthLayout;
