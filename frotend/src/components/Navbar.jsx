import React, { useState, useEffect } from 'react';
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Badge,
  Menu,
  MenuItem,
  Box,
  Avatar,
  Tooltip,
  Divider,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import {
  Menu as MenuIcon,
  Notifications as NotificationsIcon,
  Brightness4 as DarkIcon,
  Brightness7 as LightIcon,
  Logout as LogoutIcon,
  AccountCircle as ProfileIcon,
  CheckCircleOutline as CheckIcon,
  WorkOutline as WorkIcon,
  CalendarToday as DateIcon,
} from '@mui/icons-material';
import { useAuth } from '../context/AuthContext';
import { useThemeContext } from '../context/ThemeContext';
import { useNavigate } from 'react-router-dom';
import notificationService from '../services/notificationService';

const Navbar = ({ onDrawerToggle }) => {
  const { user, logout } = useAuth();
  const { darkMode, toggleTheme } = useThemeContext();
  const navigate = useNavigate();

  const [anchorElUser, setAnchorElUser] = useState(null);
  const [anchorElNotif, setAnchorElNotif] = useState(null);
  const [notifications, setNotifications] = useState([]);

  // Fetch notifications or load mock notifications if empty
  const fetchNotifications = async () => {
    try {
      if (!user) return;
      const data = await notificationService.getNotifications();
      if (data && data.length > 0) {
        setNotifications(data);
      } else {
        loadMockNotifications();
      }
    } catch (error) {
      console.warn('Could not fetch notifications, loading mock alerts.');
      loadMockNotifications();
    }
  };

  const loadMockNotifications = () => {
    setNotifications([
      { id: 1, title: 'New Drive Posted', message: 'Google has scheduled a software engineer drive.', read: false, createdAt: new Date() },
      { id: 2, title: 'Application Status Updated', message: 'You have been shortlisted for Amazon interview round 1.', read: false, createdAt: new Date() },
      { id: 3, title: 'Interview Scheduled', message: 'Technical round with Microsoft set for tomorrow 10:00 AM.', read: true, createdAt: new Date() },
    ]);
  };

  useEffect(() => {
    fetchNotifications();
  }, [user]);

  const handleOpenUserMenu = (event) => setAnchorElUser(event.currentTarget);
  const handleCloseUserMenu = () => setAnchorElUser(null);
  
  const handleOpenNotifMenu = (event) => setAnchorElNotif(event.currentTarget);
  const handleCloseNotifMenu = () => setAnchorElNotif(null);

  const handleNotificationClick = async (notif) => {
    try {
      if (!notif.read && typeof notif.id === 'number' && notif.id > 10) {
        await notificationService.markAsRead(notif.id);
      }
      // Update local state
      setNotifications(prev =>
        prev.map(n => (n.id === notif.id ? { ...n, read: true } : n))
      );
    } catch (e) {
      console.error(e);
    }
  };

  const handleLogoutClick = () => {
    handleCloseUserMenu();
    logout();
    navigate('/login');
  };

  const handleProfileClick = () => {
    handleCloseUserMenu();
    if (user?.role === 'STUDENT') {
      navigate('/student/profile');
    } else {
      navigate(`/${user?.role?.toLowerCase()}/dashboard`);
    }
  };

  const unreadCount = notifications.filter(n => !n.read).length;

  return (
    <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Toolbar>
        <IconButton
          color="inherit"
          aria-label="open drawer"
          edge="start"
          onClick={onDrawerToggle}
          sx={{ mr: 2, display: { sm: 'none' } }}
        >
          <MenuIcon />
        </IconButton>

        <Typography
          variant="h6"
          noWrap
          component="div"
          sx={{
            flexGrow: 1,
            fontWeight: 700,
            cursor: 'pointer',
            letterSpacing: 1,
            background: 'linear-gradient(90deg, #3B82F6 0%, #10B981 100%)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
            display: 'flex',
            alignItems: 'center',
            gap: 1
          }}
          onClick={() => navigate('/')}
        >
          <span>🎯</span> CareerGate
        </Typography>

        <Box display="flex" alignItems="center" gap={1.5}>
          {/* Theme Toggle */}
          <Tooltip title={darkMode ? 'Switch to Light Mode' : 'Switch to Dark Mode'}>
            <IconButton onClick={toggleTheme} color="inherit">
              {darkMode ? <LightIcon /> : <DarkIcon />}
            </IconButton>
          </Tooltip>

          {/* Notifications Bell */}
          <Tooltip title="Notifications">
            <IconButton onClick={handleOpenNotifMenu} color="inherit">
              <Badge badgeContent={unreadCount} color="error">
                <NotificationsIcon />
              </Badge>
            </IconButton>
          </Tooltip>

          <Menu
            anchorEl={anchorElNotif}
            open={Boolean(anchorElNotif)}
            onClose={handleCloseNotifMenu}
            PaperProps={{
              sx: { width: 320, maxHeight: 400, mt: 1.5 }
            }}
          >
            <Box sx={{ p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <Typography variant="subtitle1" fontWeight={700}>Notifications</Typography>
              <Typography variant="caption" color="primary" sx={{ cursor: 'pointer' }} onClick={() => setNotifications(prev => prev.map(n => ({ ...n, read: true })))}>
                Mark all read
              </Typography>
            </Box>
            <Divider />
            {notifications.length === 0 ? (
              <MenuItem disabled sx={{ py: 2 }}>
                <ListItemText primary="No new notifications" />
              </MenuItem>
            ) : (
              notifications.map((notif) => (
                <MenuItem
                  key={notif.id}
                  onClick={() => handleNotificationClick(notif)}
                  sx={{
                    whiteSpace: 'normal',
                    backgroundColor: notif.read ? 'inherit' : (theme) => theme.palette.mode === 'dark' ? 'rgba(59, 130, 246, 0.05)' : 'rgba(37, 99, 235, 0.04)',
                    py: 1.5,
                  }}
                >
                  <ListItemIcon>
                    {notif.title.includes('Drive') ? (
                      <WorkIcon color="primary" fontSize="small" />
                    ) : notif.title.includes('Status') ? (
                      <CheckIcon color="success" fontSize="small" />
                    ) : (
                      <DateIcon color="secondary" fontSize="small" />
                    )}
                  </ListItemIcon>
                  <ListItemText
                    primary={
                      <Typography variant="body2" fontWeight={notif.read ? 400 : 600}>
                        {notif.title}
                      </Typography>
                    }
                    secondary={
                      <Box>
                        <Typography variant="caption" color="text.secondary" display="block">
                          {notif.message}
                        </Typography>
                      </Box>
                    }
                  />
                </MenuItem>
              ))
            )}
          </Menu>

          {/* User profile actions */}
          {user && (
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <Tooltip title="User settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0, ml: 1 }}>
                  <Avatar
                    sx={{
                      bgcolor: 'primary.main',
                      color: '#fff',
                      fontWeight: 600,
                      width: 38,
                      height: 38
                    }}
                  >
                    {user.name ? user.name.charAt(0).toUpperCase() : 'U'}
                  </Avatar>
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: '45px' }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                <Box sx={{ px: 2, py: 1.5 }}>
                  <Typography variant="subtitle1" fontWeight={700} noWrap>
                    {user.name}
                  </Typography>
                  <Typography variant="body2" color="text.secondary" noWrap>
                    {user.email}
                  </Typography>
                  <Box
                    sx={{
                      display: 'inline-block',
                      bgcolor: (theme) => theme.palette.mode === 'dark' ? '#1E293B' : '#E2E8F0',
                      color: (theme) => theme.palette.mode === 'dark' ? '#94A3B8' : '#475569',
                      px: 1.2,
                      py: 0.3,
                      borderRadius: 1,
                      fontSize: '0.7rem',
                      fontWeight: 700,
                      mt: 0.8,
                      textTransform: 'uppercase'
                    }}
                  >
                    {user.role}
                  </Box>
                </Box>
                <Divider />
                <MenuItem onClick={handleProfileClick}>
                  <ListItemIcon>
                    <ProfileIcon fontSize="small" />
                  </ListItemIcon>
                  <Typography textAlign="center">Profile / Dashboard</Typography>
                </MenuItem>
                <MenuItem onClick={handleLogoutClick}>
                  <ListItemIcon>
                    <LogoutIcon fontSize="small" color="error" />
                  </ListItemIcon>
                  <Typography textAlign="center" color="error.main">Logout</Typography>
                </MenuItem>
              </Menu>
            </Box>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
