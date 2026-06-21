import React from 'react';
import { Box, Typography, Link } from '@mui/material';

const Footer = () => {
  return (
    <Box
      component="footer"
      sx={{
        py: 3,
        px: 2,
        mt: 'auto',
        textAlign: 'center',
        borderTop: (theme) =>
          theme.palette.mode === 'dark' ? '1px solid rgba(255, 255, 255, 0.05)' : '1px solid rgba(226, 232, 240, 0.8)',
        backgroundColor: (theme) =>
          theme.palette.mode === 'dark' ? 'rgba(15, 23, 42, 0.6)' : 'rgba(255, 255, 255, 0.6)',
      }}
    >
      <Typography variant="body2" color="text.secondary">
        {'© '}
        <Link color="inherit" href="/" sx={{ fontWeight: 500 }}>
          CareerGate Placement Management
        </Link>{' '}
        {new Date().getFullYear()}
        {'. All rights reserved.'}
      </Typography>
    </Box>
  );
};

export default Footer;
