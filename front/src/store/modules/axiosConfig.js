import axios from 'axios';

const API_BASE_URL = 'http://localhost:8085';

const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000, // Tiempo de espera (opcional)
    headers: {
        'Content-Type': 'application/json'
    }
});

export default axiosInstance;
