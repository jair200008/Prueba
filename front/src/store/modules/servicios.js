import axiosInstance from './axiosConfig.js';
import { handleApiError } from './errorHandler.js';

const buscarCoincidencias = async (searchQuery) => {
    try {
        const response = await axiosInstance.get(`/servicios?search=${searchQuery}`);
        return response.data;
    } catch (error) {
        handleApiError(error);
    }
};

const enviarRepuesto = async (repuesto) => {
    try {
        const response = await axiosInstance.post('/items/repuestos', repuesto);
        return response.data;
    } catch (error) {
        handleApiError(error);
    }
};

const enviarServicio = async (servicio) => {
    try {
        const response = await axiosInstance.post('/items/servicios', servicio);
        return response.data;
    } catch (error) {
        handleApiError(error);
    }
};

export default {
    buscarCoincidencias,
    enviarRepuesto,
    enviarServicio
};
