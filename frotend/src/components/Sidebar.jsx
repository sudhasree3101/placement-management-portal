import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import {
  Box,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Divider,
  Button,
} from '@mui/material';
import {
  Dashboard as DashboardIcon,
  People as StudentsIcon,
  Business as RecruitersIcon,
  Work as DrivesIcon,
  AssignmentTurnedIn as ApplicationsIcon,
  HistoryEdu as RecordsIcon,
  BarChart as AnalyticsIcon,
  Person as ProfileIcon,
  Assignment as AvailableIcon,
  BookmarkBorder as AppliedIcon,
  Timeline as StatusIcon,
  Logout as LogoutIcon,
} from '@mui/icons-material';
import { useAuth } from '../context/AuthContext';

const drawerWidth = 260;

const Sidebar = ({ mobileOpen, onDrawerToggle, window: windowProp }) => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const getMenuLinks = () => {
    switch (user?.role) {
      case 'ADMIN':
        return [
          { text: 'Dashboard', path: '/admin/dashboard', icon: <DashboardIcon /> },
          { text: 'Students', path: '/admin/students', icon: <StudentsIcon /> },
          { text: 'Recruiters', path: '/admin/recruiters', icon: <RecruitersIcon /> },
          { text: 'Placement Drives', path: '/admin/drives', icon: <DrivesIcon /> },
          { text: 'Applications', path: '/admin/applications', icon: <ApplicationsIcon /> },
          { text: 'Placement Records', path: '/admin/placement-records', icon: <RecordsIcon /> },
          { text: 'Analytics', path: '/admin/analytics', icon: <AnalyticsIcon /> },
        ];
      case 'STUDENT':
        return [
          { text: 'Dashboard', path: '/student/dashboard', icon: <DashboardIcon /> },
          { text: 'Profile', path: '/student/profile', icon: <ProfileIcon /> },
          { text: 'Available Drives', path: '/student/available-drives', icon: <AvailableIcon /> },
          { text: 'Applied Drives', path: '/student/applied-drives', icon: <AppliedIcon /> },
          { text: 'Interview Status', path: '/student/interview-status', icon: <StatusIcon /> },
        ];
      case 'RECRUITER':
        return [
          { text: 'Dashboard', path: '/recruiter/dashboard', icon: <DashboardIcon /> },
          { text: 'Manage Drives', path: '/recruiter/manage-drives', icon: <DrivesIcon /> },
          { text: 'Applicants', path: '/recruiter/applicants', icon: <ApplicationsIcon /> },
          { text: 'Results', path: '/recruiter/results', icon: <RecordsIcon /> },
        ];
      default:
        return [];
    }
  };

  const drawerContent = (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      <Toolbar />
      <Box sx={{ p: 2.5, textAlign: 'center' }}>
        <Box
          sx={{
            fontWeight: 700,
            fontSize: '0.9rem',
            letterSpacing: 1,
            color: 'primary.light',
            textTransform: 'uppercase',
            mb: 0.5,
          }}
        >
          {user?.role} PORTAL
        </Box>
        <Box sx={{ fontSize: '0.8rem', color: '#94A3B8' }} noWrap>
          {user?.email}
        </Box>
      </Box>
      <Divider sx={{ borderColor: 'rgba(255, 255, 255, 0.08)' }} />
      
      <List sx={{ flexGrow: 1, px: 1.5, py: 2 }}>
        {getMenuLinks().map((item) => (
          <ListItem key={item.text} disablePadding sx={{ mb: 0.8 }}>
            <ListItemButton
              component={NavLink}
              to={item.path}
              onClick={mobileOpen ? onDrawerToggle : undefined}
              sx={{
                borderRadius: '8px',
                color: '#94A3B8',
                '&.active': {
                  backgroundColor: 'primary.main',
                  color: '#FFFFFF',
                  '& .MuiListItemIcon-root': {
                    color: '#FFFFFF',
                  },
                },
                '&:hover:not(.active)': {
                  backgroundColor: 'rgba(255, 255, 255, 0.05)',
                  color: '#FFFFFF',
                  '& .MuiListItemIcon-root': {
                    color: '#FFFFFF',
                  },
                },
              }}
            >
              <ListItemIcon sx={{ color: '#64748B', minWidth: 40 }}>
                {item.icon}
              </ListItemIcon>
              <ListItemText
                primary={item.text}
                primaryTypographyProps={{ fontSize: '0.925rem', fontWeight: 500 }}
              />
            </ListItemButton>
          </ListItem>
        ))}
      </List>

      <Box sx={{ p: 2 }}>
        <Button
          fullWidth
          variant="contained"
          color="error"
          startIcon={<LogoutIcon />}
          onClick={handleLogout}
          sx={{
            py: 1,
            borderRadius: '8px',
            backgroundColor: '#EF4444',
            '&:hover': {
              backgroundColor: '#DC2626',
            },
          }}
        >
          Logout
        </Button>
      </Box>
    </Box>
  );

  const container = windowProp !== undefined ? () => windowProp().document.body : undefined;

  return (
    <Box
      component="nav"
      sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
      aria-label="mailbox folders"
    >
      {/* Mobile Drawer */}
      <Drawer
        container={container}
        variant="temporary"
        open={mobileOpen}
        onClose={onDrawerToggle}
        ModalProps={{
          keepMounted: true, // Better open performance on mobile.
        }}
        sx={{
          display: { xs: 'block', sm: 'none' },
          '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
        }}
      >
        {drawerContent}
      </Drawer>

      {/* Desktop Drawer */}
      <Drawer
        variant="permanent"
        sx={{
          display: { xs: 'none', sm: 'block' },
          '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
        }}
        open
      >
        {drawerContent}
      </Drawer>
    </Box>
  );
};

export default Sidebar;
