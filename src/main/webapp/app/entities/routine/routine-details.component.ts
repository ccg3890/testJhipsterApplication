import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRoutine } from '@/shared/model/routine.model';
import RoutineService from './routine.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RoutineDetails extends Vue {
  @Inject('routineService') private routineService: () => RoutineService;
  @Inject('alertService') private alertService: () => AlertService;

  public routine: IRoutine = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routineId) {
        vm.retrieveRoutine(to.params.routineId);
      }
    });
  }

  public retrieveRoutine(routineId) {
    this.routineService()
      .find(routineId)
      .then(res => {
        this.routine = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
