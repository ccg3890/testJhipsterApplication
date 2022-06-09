<template>
  <div>
    <h2 id="page-heading" data-cy="RoutineHeading">
      <span v-text="$t('testJhipsterApplicationApp.routine.home.title')" id="routine-heading">Routines</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('testJhipsterApplicationApp.routine.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RoutineCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-routine"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('testJhipsterApplicationApp.routine.home.createLabel')"> Create a new Routine </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && routines && routines.length === 0">
      <span v-text="$t('testJhipsterApplicationApp.routine.home.notFound')">No routines found</span>
    </div>
    <div class="table-responsive" v-if="routines && routines.length > 0">
      <table class="table table-striped" aria-describedby="routines">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('testJhipsterApplicationApp.routine.register')">Register</span></th>
            <th scope="row"><span v-text="$t('testJhipsterApplicationApp.routine.type')">Type</span></th>
            <th scope="row"><span v-text="$t('testJhipsterApplicationApp.routine.desc')">Desc</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="routine in routines" :key="routine.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RoutineView', params: { routineId: routine.id } }">{{ routine.id }}</router-link>
            </td>
            <td>{{ routine.register }}</td>
            <td>{{ routine.type }}</td>
            <td>{{ routine.desc }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RoutineView', params: { routineId: routine.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RoutineEdit', params: { routineId: routine.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(routine)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="testJhipsterApplicationApp.routine.delete.question"
          data-cy="routineDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-routine-heading" v-text="$t('testJhipsterApplicationApp.routine.delete.question', { id: removeId })">
          Are you sure you want to delete this Routine?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-routine"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRoutine()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./routine.component.ts"></script>
