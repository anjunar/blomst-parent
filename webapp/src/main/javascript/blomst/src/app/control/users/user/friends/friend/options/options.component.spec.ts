import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendsOptionsComponent } from './options.component';

describe('OptionsComponent', () => {
  let component: FriendsOptionsComponent;
  let fixture: ComponentFixture<FriendsOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FriendsOptionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FriendsOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
