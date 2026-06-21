import api from '../api/axiosConfig';

const driveService = {
  createDrive: async (driveDto) => {
    const response = await api.post('/drives', driveDto);
    return response.data;
  },
  getDriveById: async (id) => {
    const response = await api.get(`/drives/${id}`);
    return response.data;
  },
  updateDrive: async (id, driveDto) => {
    const response = await api.put(`/drives/${id}`, driveDto);
    return response.data;
  },
  deleteDrive: async (id) => {
    const response = await api.delete(`/drives/${id}`);
    return response.data;
  },
  getAllDrives: async (params) => {
    const response = await api.get('/drives', { params });
    return response.data;
  },
};

export default driveService;
