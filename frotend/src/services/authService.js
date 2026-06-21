import api from '../api/axiosConfig';

const authService = {
  login: async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    return response.data;
  },
  registerStudent: async (studentData) => {
    const response = await api.post('/auth/register/student', studentData);
    return response.data;
  },
  registerRecruiter: async (recruiterData) => {
    const response = await api.post('/auth/register/recruiter', recruiterData);
    return response.data;
  },
  forgotPassword: async (email) => {
    const response = await api.post('/auth/forgot-password', { email });
    return response.data;
  },
  resetPassword: async (token, password) => {
    const response = await api.post('/auth/reset-password', { token, password });
    return response.data;
  },
  logout: () => {
    // Handled in Context API by clearing local storage
  }
};

export default authService;
