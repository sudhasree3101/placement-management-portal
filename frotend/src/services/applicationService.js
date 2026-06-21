import api from '../api/axiosConfig';

const applicationService = {
  applyForDrive: async (applicationDto) => {
    const response = await api.post('/applications/apply', applicationDto);
    return response.data;
  },
  getApplicationsByStudentId: async (studentId) => {
    const response = await api.get(`/applications/student/${studentId}`);
    return response.data;
  },
  getApplicationsByDriveId: async (driveId) => {
    const response = await api.get(`/applications/drive/${driveId}`);
    return response.data;
  },
  updateApplicationStatus: async (applicationId, status) => {
    // Backend takes ApplicationStatusRequest, likely: { applicationId, status }
    const response = await api.put('/applications/status', { applicationId, status });
    return response.data;
  },
  getAllApplications: async () => {
    const response = await api.get('/applications');
    return response.data;
  },
  getApplicationById: async (id) => {
    const response = await api.get(`/applications/${id}`);
    return response.data;
  },
  
  // Interview round sub-endpoints
  scheduleInterviewRound: async (roundDto) => {
    const response = await api.post('/interviews', roundDto);
    return response.data;
  },
  getInterviewRoundsByApplication: async (applicationId) => {
    const response = await api.get(`/interviews/application/${applicationId}`);
    return response.data;
  },
  updateInterviewRound: async (id, roundDto) => {
    const response = await api.put(`/interviews/${id}`, roundDto);
    return response.data;
  },
  deleteInterviewRound: async (id) => {
    const response = await api.delete(`/interviews/${id}`);
    return response.data;
  }
};

export default applicationService;
