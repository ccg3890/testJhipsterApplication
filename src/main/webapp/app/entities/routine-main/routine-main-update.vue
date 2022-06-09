<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testJhipsterApplicationApp.routineMain.home.createOrEditLabel"
          data-cy="RoutineMainCreateUpdateHeading"
          v-text="$t('testJhipsterApplicationApp.routineMain.home.createOrEditLabel')"
        >
          Create or edit a RoutineMain
        </h2>
        <div>
          <div class="form-group" v-if="routineMain.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="routineMain.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testJhipsterApplicationApp.routineMain.registerid')" for="routine-main-registerid"
              >Registerid</label
            >
            <input
              type="text"
              class="form-control"
              name="registerid"
              id="routine-main-registerid"
              data-cy="registerid"
              :class="{ valid: !$v.routineMain.registerid.$invalid, invalid: $v.routineMain.registerid.$invalid }"
              v-model="$v.routineMain.registerid.$model"
              required
            />
            <div v-if="$v.routineMain.registerid.$anyDirty && $v.routineMain.registerid.$invalid">
              <small class="form-text text-danger" v-if="!$v.routineMain.registerid.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('testJhipsterApplicationApp.routineMain.description')"
              for="routine-main-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="routine-main-description"
              data-cy="description"
              :class="{ valid: !$v.routineMain.description.$invalid, invalid: $v.routineMain.description.$invalid }"
              v-model="$v.routineMain.description.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.routineMain.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./routine-main-update.component.ts"></script>
