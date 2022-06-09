import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const RoutineMain = () => import('@/entities/routine-main/routine-main.vue');
// prettier-ignore
const RoutineMainUpdate = () => import('@/entities/routine-main/routine-main-update.vue');
// prettier-ignore
const RoutineMainDetails = () => import('@/entities/routine-main/routine-main-details.vue');
// prettier-ignore
const Routine = () => import('@/entities/routine/routine.vue');
// prettier-ignore
const RoutineUpdate = () => import('@/entities/routine/routine-update.vue');
// prettier-ignore
const RoutineDetails = () => import('@/entities/routine/routine-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'routine-main',
      name: 'RoutineMain',
      component: RoutineMain,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine-main/new',
      name: 'RoutineMainCreate',
      component: RoutineMainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine-main/:routineMainId/edit',
      name: 'RoutineMainEdit',
      component: RoutineMainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine-main/:routineMainId/view',
      name: 'RoutineMainView',
      component: RoutineMainDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine',
      name: 'Routine',
      component: Routine,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine/new',
      name: 'RoutineCreate',
      component: RoutineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine/:routineId/edit',
      name: 'RoutineEdit',
      component: RoutineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'routine/:routineId/view',
      name: 'RoutineView',
      component: RoutineDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
