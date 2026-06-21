import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Box, Toolbar, CssBaseline } from '@mui/material';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';

const drawerWidth = 260;

const DashboardLayout = () => {
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', flexDirection: 'column' }}>
      <CssBaseline />
      
      {/* Navbar */}
      <Navbar onDrawerToggle={handleDrawerToggle} />

      {/* Main Container */}
      <Box sx={{ display: 'flex', flex: 1 }}>
        {/* Sidebar */}
        <Sidebar mobileOpen={mobileOpen} onDrawerToggle={handleDrawerToggle} />

        {/* Dashboard Content Panel */}
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            p: { xs: 2, sm: 3 },
            width: { sm: `calc(100% - ${drawerWidth}px)` },
            display: 'flex',
            flexDirection: 'column',
            minHeight: '100%',
          }}
        >
          {/* Offset toolbar height so elements don't hide below header */}
          <Toolbar />
          
          <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
            <Outlet />
          </Box>

          <Footer />
        </Box>
      </Box>
    </Box>
  );
};

export default DashboardLayout;
