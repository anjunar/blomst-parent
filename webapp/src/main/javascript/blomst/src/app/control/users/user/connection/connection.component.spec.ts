import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserConnectionComponent } from './connection.component';

describe('ConnectionComponent', () => {
  let component: UserConnectionComponent;
  let fixture: ComponentFixture<UserConnectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserConnectionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserConnectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
