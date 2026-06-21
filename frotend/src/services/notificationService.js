import api from '../api/axiosConfig';

const notificationService = {
  getNotifications: async () => {
    const response = await api.get('/notifications');
    return response.data;
  },
  getUnreadNotifications: async () => {
    const response = await api.get('/notifications/unread');
    return response.data;
  },
  markAsRead: async (id) => {
    const response = await api.put(`/notifications/${id}/read`);
    return response.data;
  },
  createNotification: async (notificationDto) => {
    const response = await api.post('/notifications', notificationDto);
    return response.data;
  },
  deleteNotification: async (id) => {
    const response = await api.delete(`/notifications/${id}`);
    return response.data;
  }
};

export default notificationService;
