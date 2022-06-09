import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRoutineMain } from '@/shared/model/routine-main.model';
import RoutineMainService from './routine-main.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RoutineMainDetails extends Vue {
  @Inject('routineMainService') private routineMainService: () => RoutineMainService;
  @Inject('alertService') private alertService: () => AlertService;

  public routineMain: IRoutineMain = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routineMainId) {
        vm.retrieveRoutineMain(to.params.routineMainId);
      }
    });
  }

  public retrieveRoutineMain(routineMainId) {
    this.routineMainService()
      .find(routineMainId)
      .then(res => {
        this.routineMain = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
