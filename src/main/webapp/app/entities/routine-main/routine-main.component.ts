import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRoutineMain } from '@/shared/model/routine-main.model';

import RoutineMainService from './routine-main.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RoutineMain extends Vue {
  @Inject('routineMainService') private routineMainService: () => RoutineMainService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public routineMains: IRoutineMain[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRoutineMains();
  }

  public clear(): void {
    this.retrieveAllRoutineMains();
  }

  public retrieveAllRoutineMains(): void {
    this.isFetching = true;
    this.routineMainService()
      .retrieve()
      .then(
        res => {
          this.routineMains = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IRoutineMain): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRoutineMain(): void {
    this.routineMainService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('testJhipsterApplicationApp.routineMain.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRoutineMains();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
