import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import RoutineMainService from './routine-main/routine-main.service';
import RoutineService from './routine/routine.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('routineMainService') private routineMainService = () => new RoutineMainService();
  @Provide('routineService') private routineService = () => new RoutineService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
