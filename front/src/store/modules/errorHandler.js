export const handleApiError = (error) => {
    console.error("Error en la API:", error);
    throw error.response ? error.response.data : error.message;
};