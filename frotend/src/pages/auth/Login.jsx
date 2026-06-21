import React, { useState } from 'react';
import { useNavigate, Link as RouterLink, useLocation } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import {
  TextField,
  Button,
  FormControlLabel,
  Checkbox,
  InputAdornment,
  IconButton,
  Typography,
  Box,
  Link,
  Alert,
} from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useAuth } from '../../context/AuthContext';
import { EMAIL_VALIDATION, PASSWORD_VALIDATION } from '../../utils/validators';

const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: {
      email: '',
      password: '',
      rememberMe: true,
    },
  });

  // Where to redirect after login (default is the corresponding dashboard based on role)
  const from = location.state?.from?.pathname || '/';

  const onSubmit = async (data) => {
    setErrorMessage('');
    setIsSubmitting(true);
    try {
      const loggedUser = await login(data.email, data.password, data.rememberMe);
      // Redirect to correct dashboard based on role
      const role = loggedUser.role;
      if (from !== '/') {
        navigate(from, { replace: true });
      } else {
        navigate(`/${role.toLowerCase()}/dashboard`, { replace: true });
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(
        error.response?.data?.message || 'Invalid email or password. Please try again.'
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  const togglePasswordVisibility = () => setShowPassword((prev) => !prev);

  return (
    <Box>
      <Typography variant="h5" component="h2" align="center" sx={{ fontWeight: 700, mb: 3 }}>
        Sign In to Portal
      </Typography>

      {errorMessage && (
        <Alert severity="error" sx={{ mb: 3, borderRadius: '8px' }}>
          {errorMessage}
        </Alert>
      )}

      <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          margin="normal"
          required
          fullWidth
          id="email"
          label="Email Address"
          name="email"
          autoComplete="email"
          autoFocus
          error={!!errors.email}
          helperText={errors.email?.message}
          {...register('email', EMAIL_VALIDATION)}
          sx={{ mb: 2 }}
        />

        <TextField
          margin="normal"
          required
          fullWidth
          name="password"
          label="Password"
          type={showPassword ? 'text' : 'password'}
          id="password"
          autoComplete="current-password"
          error={!!errors.password}
          helperText={errors.password?.message}
          {...register('password', PASSWORD_VALIDATION)}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={togglePasswordVisibility}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            ),
          }}
          sx={{ mb: 1.5 }}
        />

        <Box display="flex" justifyContent="space-between" alignItems="center" sx={{ mb: 2.5 }}>
          <FormControlLabel
            control={
              <Checkbox
                value="remember"
                color="primary"
                {...register('rememberMe')}
              />
            }
            label={
              <Typography variant="body2" color="text.secondary">
                Remember me
              </Typography>
            }
          />
          <Link
            component={RouterLink}
            to="/forgot-password"
            variant="body2"
            sx={{ fontWeight: 600, textDecoration: 'none' }}
          >
            Forgot password?
          </Link>
        </Box>

        <Button
          type="submit"
          fullWidth
          variant="contained"
          size="large"
          disabled={isSubmitting}
          sx={{
            py: 1.2,
            mb: 3,
            fontSize: '1rem',
            background: 'linear-gradient(90deg, #2563EB 0%, #1D4ED8 100%)',
            boxShadow: '0 4px 14px 0 rgba(37, 99, 235, 0.4)',
          }}
        >
          {isSubmitting ? 'Signing In...' : 'Sign In'}
        </Button>

        <Box sx={{ mt: 2, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
            New Student?{' '}
            <Link component={RouterLink} to="/register/student" sx={{ fontWeight: 600, textDecoration: 'none' }}>
              Register as Student
            </Link>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Looking to Hire?{' '}
            <Link component={RouterLink} to="/register/recruiter" sx={{ fontWeight: 600, textDecoration: 'none' }}>
              Register as Recruiter
            </Link>
          </Typography>
        </Box>
      </Box>
    </Box>
  );
};

export default Login;
