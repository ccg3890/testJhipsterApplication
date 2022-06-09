<template>
  <div>
    <b-dropdown-item to="/routine-main">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.routineMain')">Routine Main</span>
    </b-dropdown-item>
    <b-dropdown-item to="/routine">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.routine')">Routine</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
