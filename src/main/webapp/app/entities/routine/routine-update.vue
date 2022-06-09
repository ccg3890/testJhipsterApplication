<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testJhipsterApplicationApp.routine.home.createOrEditLabel"
          data-cy="RoutineCreateUpdateHeading"
          v-text="$t('testJhipsterApplicationApp.routine.home.createOrEditLabel')"
        >
          Create or edit a Routine
        </h2>
        <div>
          <div class="form-group" v-if="routine.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="routine.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testJhipsterApplicationApp.routine.register')" for="routine-register"
              >Register</label
            >
            <input
              type="text"
              class="form-control"
              name="register"
              id="routine-register"
              data-cy="register"
              :class="{ valid: !$v.routine.register.$invalid, invalid: $v.routine.register.$invalid }"
              v-model="$v.routine.register.$model"
              required
            />
            <div v-if="$v.routine.register.$anyDirty && $v.routine.register.$invalid">
              <small class="form-text text-danger" v-if="!$v.routine.register.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testJhipsterApplicationApp.routine.type')" for="routine-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="routine-type"
              data-cy="type"
              :class="{ valid: !$v.routine.type.$invalid, invalid: $v.routine.type.$invalid }"
              v-model="$v.routine.type.$model"
              required
            />
            <div v-if="$v.routine.type.$anyDirty && $v.routine.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.routine.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testJhipsterApplicationApp.routine.desc')" for="routine-desc">Desc</label>
            <input
              type="text"
              class="form-control"
              name="desc"
              id="routine-desc"
              data-cy="desc"
              :class="{ valid: !$v.routine.desc.$invalid, invalid: $v.routine.desc.$invalid }"
              v-model="$v.routine.desc.$model"
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
            :disabled="$v.routine.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./routine-update.component.ts"></script>
