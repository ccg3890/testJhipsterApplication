<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="routineMain">
        <h2 class="jh-entity-heading" data-cy="routineMainDetailsHeading">
          <span v-text="$t('testJhipsterApplicationApp.routineMain.detail.title')">RoutineMain</span> {{ routineMain.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testJhipsterApplicationApp.routineMain.registerid')">Registerid</span>
          </dt>
          <dd>
            <span>{{ routineMain.registerid }}</span>
          </dd>
          <dt>
            <span v-text="$t('testJhipsterApplicationApp.routineMain.description')">Description</span>
          </dt>
          <dd>
            <span>{{ routineMain.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="routineMain.id"
          :to="{ name: 'RoutineMainEdit', params: { routineMainId: routineMain.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./routine-main-details.component.ts"></script>
