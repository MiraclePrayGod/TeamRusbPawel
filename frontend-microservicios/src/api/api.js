import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8085', // Asegúrate que coincida con tu puerto de Spring
});

// Interceptor para errores
api.interceptors.response.use(
    response => response,
    error => {
        if (error.response?.data?.message) {
            return Promise.reject(new Error(error.response.data.message));
        }
        return Promise.reject(error);
    }
);

export default api;