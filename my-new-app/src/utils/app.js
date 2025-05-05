import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Thêm Request Interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); 

    const publicEndpoints = [
      '/auth/token',
      '/users'
    ];

    const isPublicEndpoint = publicEndpoints.some((endpoint) =>
      config.url.includes(endpoint)
    );

    if (token && !isPublicEndpoint) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (isPublicEndpoint && config.headers.Authorization) {
      delete config.headers.Authorization;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
