import { ref } from 'vue';

export function useAlerts() {
    const alertMessage = ref('');
    const alertType = ref('');

    // Configuración por defecto para la duración de la alerta
    const DEFAULT_DURATION = 5000;

    // Función para mostrar alerta
    const showAlert = (message = '', type = 'info', duration = DEFAULT_DURATION) => {
        alertMessage.value = message;
        alertType.value = type;

        // Oculta la alerta después del tiempo especificado
        setTimeout(() => {
            hideAlert();
        }, duration);
    };

    // Función para ocultar alerta
    const hideAlert = () => {
        alertMessage.value = '';
        alertType.value = '';
    };

    return {
        alertMessage,
        alertType,
        showAlert,
        hideAlert
    };
}
