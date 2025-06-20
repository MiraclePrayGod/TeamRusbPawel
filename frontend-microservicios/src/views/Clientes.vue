<template>
  <v-container>
    <v-card class="pa-4">
      <!-- Header -->
      <v-row class="mb-4" align="center">
        <v-col cols="12" md="6">
          <h1 class="text-h4">Gestión de Clientes</h1>
        </v-col>
        <v-col cols="12" md="6" class="text-right">
          <v-btn color="primary" @click="openDialog({})">
            <v-icon start>mdi-plus</v-icon>
            Nuevo Cliente
          </v-btn>
        </v-col>
      </v-row>

      <!-- Search -->
      <v-text-field
          v-model="search"
          label="Buscar por nombre, documento o correo"
          prepend-inner-icon="mdi-magnify"
          clearable
          outlined
          class="mb-4"
      />

      <!-- Data Table -->
      <v-data-table
          :headers="headers"
          :items="clients"
          :search="search"
          :loading="loading"
          :items-per-page="10"
      >
        <!-- Status -->
        <template #item.status="{ item }">
          <v-chip :color="item.status ? 'green' : 'red'" small>
            {{ item.status ? 'Activo' : 'Inactivo' }}
          </v-chip>
        </template>

        <!-- Date -->
        <template #item.registrationDate="{ item }">
          {{ formatDate(item.registrationDate) }}
        </template>

        <!-- Actions -->
        <template #item.actions="{ item }">
          <v-tooltip text="Editar">
            <template #activator="{ props }">
              <v-btn
                  v-bind="props"
                  icon
                  variant="text"
                  color="blue"
                  size="small"
                  @click="openDialog(item)"
              >
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </template>
          </v-tooltip>

          <v-tooltip text="Eliminar">
            <template #activator="{ props }">
              <v-btn
                  v-bind="props"
                  icon
                  variant="text"
                  color="red"
                  size="small"
                  @click="confirmDelete(item)"
                  class="ml-2"
              >
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </template>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card>

    <!-- Form Dialog -->
    <v-dialog v-model="dialog" max-width="600px">
      <v-card>
        <v-card-title>
          {{ formTitle }}
        </v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-row>
              <v-col cols="6">
                <v-text-field
                    v-model="editedClient.firstNames"
                    label="Nombres"
                    :rules="[required]"
                />
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="editedClient.lastNames"
                    label="Apellidos"
                />
              </v-col>
              <v-col cols="6">
                <v-select
                    v-model="editedClient.documentType"
                    label="Tipo Documento"
                    :items="['DNI', 'RUC', 'CE']"
                    :rules="[required]"
                />
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="editedClient.documentNumber"
                    label="Número Documento"
                    :rules="[required]"
                />
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="editedClient.email"
                    label="Correo"
                    type="email"
                    :rules="[emailRules]"
                />
              </v-col>
              <v-col cols="6">
                <v-text-field
                    v-model="editedClient.phone"
                    label="Teléfono"
                />
              </v-col>
              <v-col cols="12">
                <v-text-field
                    v-model="editedClient.address"
                    label="Dirección"
                />
              </v-col>
              <v-col cols="6">
                <v-switch
                    v-model="editedClient.status"
                    label="Estado Activo"
                    color="green"
                />
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn color="grey" @click="dialog = false">Cancelar</v-btn>
          <v-btn color="primary" @click="saveClient">Guardar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Delete Confirmation -->
    <v-dialog v-model="deleteDialog" max-width="400px">
      <v-card>
        <v-card-title>Confirmar Eliminación</v-card-title>
        <v-card-text>
          ¿Estás seguro de eliminar al cliente {{ clientToDelete?.firstNames }}?
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn color="grey" @click="deleteDialog = false">Cancelar</v-btn>
          <v-btn color="red" @click="deleteClient">Eliminar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import api from '@/api/api';

// State
const search = ref('');
const loading = ref(false);
const dialog = ref(false);
const deleteDialog = ref(false);
const form = ref(null);
const clients = ref([]);
const editedClient = ref({});
const clientToDelete = ref(null);

// Validation
const required = (value) => !!value || 'Campo requerido';
const emailRules = (value) => /.+@.+\..+/.test(value) || 'Correo inválido';

// Headers
const headers = [
  { title: 'ID', key: 'id', width: '80px' },
  { title: 'Nombres', key: 'firstNames' },
  { title: 'Apellidos', key: 'lastNames' },
  { title: 'Tipo Doc.', key: 'documentType', width: '120px' },
  { title: 'N° Documento', key: 'documentNumber', width: '140px' },
  { title: 'Correo', key: 'email' },
  { title: 'Teléfono', key: 'phone', width: '130px' },
  { title: 'Dirección', key: 'address' },
  { title: 'Estado', key: 'status', align: 'center', width: '100px' },
  { title: 'Fecha Registro', key: 'registrationDate', width: '160px' },
  { title: 'Acciones', key: 'actions', sortable: false, width: '120px' },
];

// Computed
const formTitle = computed(() =>
    editedClient.value.id ? 'Editar Cliente' : 'Nuevo Cliente'
);

// Methods
const loadClients = async () => {
  try {
    loading.value = true;
    const response = await api.get('/clientes');
    clients.value = response.data;
  } catch (error) {
    console.error('Error loading clients:', error);
  } finally {
    loading.value = false;
  }
};

const formatDate = (date) => {
  return new Date(date).toLocaleDateString('es-PE', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
};

const openDialog = (client) => {
  editedClient.value = { ...client };
  dialog.value = true;
};

const saveClient = async () => {
  const { valid } = await form.value.validate();
  if (!valid) return;

  try {
    if (editedClient.value.id) {
      await api.put(`/clientes/${editedClient.value.id}`, editedClient.value);
    } else {
      await api.post('/clientes', editedClient.value);
    }
    dialog.value = false;
    loadClients();
  } catch (error) {
    console.error('Error saving client:', error.message);
  }
};

const confirmDelete = (client) => {
  clientToDelete.value = client;
  deleteDialog.value = true;
};

const deleteClient = async () => {
  try {
    await api.delete(`/clientes/${clientToDelete.value.id}`);
    deleteDialog.value = false;
    loadClients();
  } catch (error) {
    console.error('Error deleting client:', error.message);
  }
};

// Lifecycle
onMounted(() => {
  loadClients();
});
</script>

<style scoped>
.v-card {
  border-radius: 8px;
}
</style>