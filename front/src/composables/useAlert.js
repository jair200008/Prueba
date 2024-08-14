import { ref } from 'vue';

export function useAlerts() {
    const alertMessage = ref('');
    const alertType = ref('');

    const showAlert = (message, type) => {
        alertMessage.value = message;
        alertType.value = type;
        setTimeout(() => {
            hideAlert();
        }, 5000);
    };

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