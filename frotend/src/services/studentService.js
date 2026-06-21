import api from '../api/axiosConfig';

const studentService = {
  createStudent: async (studentDto) => {
    const response = await api.post('/students', studentDto);
    return response.data;
  },
  getStudentById: async (id) => {
    const response = await api.get(`/students/${id}`);
    return response.data;
  },
  updateStudent: async (id, studentDto) => {
    const response = await api.put(`/students/${id}`, studentDto);
    return response.data;
  },
  deleteStudent: async (id) => {
    const response = await api.delete(`/students/${id}`);
    return response.data;
  },
  getAllStudents: async (params) => {
    const response = await api.get('/students', { params });
    return response.data;
  },
  searchStudents: async (params) => {
    const response = await api.get('/students/search', { params });
    return response.data;
  },
};

export default studentService;
