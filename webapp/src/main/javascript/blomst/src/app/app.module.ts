import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {Angular2SimplicityModule} from "ng2-simplicity";
import {RouteReuseStrategy} from "@angular/router";
import {AppRoutingStrategyService} from "./app-routing-strategy.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppStartupService} from "./app-startup.service";
import { TimelineComponent } from './social/timeline/timeline.component';
import { LoginComponent } from './security/login/login.component';
import { LogoutComponent } from './security/logout/logout.component';
import { RegisterComponent } from './security/register/register.component';
import { SettingsComponent } from './control/settings/settings.component';
import { FormComponent } from './navigator/form/form.component';
import { TableComponent } from './navigator/table/table.component';
import { LikesComponent } from './shared/likes/likes.component';
import { HumanizePipe } from './shared/humanize.pipe';
import { LikesPopupComponent } from './shared/likes/likes-popup/likes-popup.component';
import { UserComponent } from './control/user/user.component';
import { VisibilityComponent } from './shared/visibility/visibility.component';
import { AddressComponent } from './control/user/address/address.component';
import { PostOptionsComponent } from './social/timeline/options/options.component';
import { TextPostComponent } from './social/timeline/post/text-post/text-post.component';
import { ImagePostComponent } from './social/timeline/post/image-post/image-post.component';
import { VideoPostComponent } from './social/timeline/post/video-post/video-post.component';
import { CommentsComponent } from './social/timeline/comments/comments.component';
import { TypedTemplateDirective } from './typed-template.directive';
import { CommentComponent } from './social/timeline/comments/comment/comment.component';
import { PostComponent } from './social/timeline/post/post.component';
import {CommentOptionsComponent} from "./social/timeline/comments/comment/options/options.component";
import { FriendsComponent } from './control/user/friends/friends.component';
import { FriendComponent } from './control/user/friends/friend/friend.component';
import { DetailsComponent } from './control/user/details/details.component';

function appConfigFactory(service : AppStartupService) {
  return (): Promise<any> => {
    return service.init();
  }
}

@NgModule({
  declarations: [
    TypedTemplateDirective,
    AppComponent,
    TimelineComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    SettingsComponent,
    FormComponent,
    TableComponent,
    LikesComponent,
    HumanizePipe,
    LikesPopupComponent,
    UserComponent,
    VisibilityComponent,
    AddressComponent,
    PostOptionsComponent,
    TextPostComponent,
    ImagePostComponent,
    VideoPostComponent,
    CommentsComponent,
    CommentComponent,
    PostComponent,
    CommentOptionsComponent,
    FriendsComponent,
    FriendComponent,
    DetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    Angular2SimplicityModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: AppRoutingStrategyService
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appConfigFactory,
      deps : [AppStartupService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
