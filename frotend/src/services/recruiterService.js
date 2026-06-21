import api from '../api/axiosConfig';

const recruiterService = {
  createRecruiter: async (recruiterDto) => {
    const response = await api.post('/recruiters', recruiterDto);
    return response.data;
  },
  getRecruiterById: async (id) => {
    const response = await api.get(`/recruiters/${id}`);
    return response.data;
  },
  updateRecruiter: async (id, recruiterDto) => {
    const response = await api.put(`/recruiters/${id}`, recruiterDto);
    return response.data;
  },
  deleteRecruiter: async (id) => {
    const response = await api.delete(`/recruiters/${id}`);
    return response.data;
  },
  getAllRecruiters: async () => {
    const response = await api.get('/recruiters');
    return response.data;
  },
};

export default recruiterService;
