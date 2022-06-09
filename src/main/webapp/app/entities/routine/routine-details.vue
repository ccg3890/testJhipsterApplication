<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="routine">
        <h2 class="jh-entity-heading" data-cy="routineDetailsHeading">
          <span v-text="$t('testJhipsterApplicationApp.routine.detail.title')">Routine</span> {{ routine.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testJhipsterApplicationApp.routine.register')">Register</span>
          </dt>
          <dd>
            <span>{{ routine.register }}</span>
          </dd>
          <dt>
            <span v-text="$t('testJhipsterApplicationApp.routine.type')">Type</span>
          </dt>
          <dd>
            <span>{{ routine.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('testJhipsterApplicationApp.routine.desc')">Desc</span>
          </dt>
          <dd>
            <span>{{ routine.desc }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="routine.id" :to="{ name: 'RoutineEdit', params: { routineId: routine.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./routine-details.component.ts"></script>
