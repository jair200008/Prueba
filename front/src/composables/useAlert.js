// src/composables/useAlert.js
import { ref } from 'vue';

export function useAlerts() {
    const alertMessage = ref('');
    const alertType = ref('');

    const showAlert = (message, type) => {
        alertMessage.value = message;
        alertType.value = type;
        setTimeout(() => {
            alertMessage.value = '';
            alertType.value = '';
        }, 3000);  // Duración de 3 segundos
    };

    return {
        alertMessage,
        alertType,
        showAlert
    };
}