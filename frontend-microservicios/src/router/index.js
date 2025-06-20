import { createRouter, createWebHistory } from 'vue-router';
import Clientes from '../views/Clientes.vue';

const routes = [
    { path: '/', redirect: '/clientes' },
    { path: '/clientes', name: 'Clientes', component: Clientes },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;