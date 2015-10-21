package ar.pablitar.cannonball

import com.uqbar.vainilla.GameComponent
import ar.pablitar.vainilla.appearances.Camera
import ar.pablitar.vainilla.appearances.worldspace.Rectangle
import java.awt.Color
import ar.pablitar.vainilla.commons.components.RichGameComponent
import ar.pablitar.vainilla.commons.components.SpeedyComponent
import ar.pablitar.vainilla.commons.math.Vector2D
import com.uqbar.vainilla.DeltaState
import ar.pablitar.tiles.Resources
import ar.pablitar.tiles.components.Floor
import ar.pablitar.tiles.TilesScene
import com.uqbar.vainilla.events.constants.Key

class Character(implicit val camera: Camera) extends SpeedyComponent[TilesScene] {
  this.setAppearance(Resources.standingAnimation(camera))
  this.acceleration = Some(Vector2D(0, 300))
  
  override def update(state: DeltaState) = {
    this.checkCollisionWith(this.getScene.floor, state)
    this.checkKeys(state)
    super.update(state)
  }
  
  this.setZ(20)
  
  override def height = super.height - 5

  def checkKeys(state: DeltaState) = {
    speed.x1 = 0
    if(state.isKeyBeingHold(Key.LEFT)) {
      this.speed.x1 = -200
    }
    
    if(state.isKeyBeingHold(Key.RIGHT)) {
      this.speed.x1 = 200
    }
    
    if(state.isKeyBeingHold(Key.SPACE)) {
      this.speed.x2 = -200
    }
  }
  
  def checkCollisionWith(floor: Floor, state: DeltaState) = {
    val afterPosition = this.positionAfterSpeed(state)
    if(!floor.puntoEstaDetras(this.bottomLeft()) && floor.puntoEstaDetras(this.bottomLeft(afterPosition))
        && this.getX > floor.topLeft().x1 && this.getX < floor.topRight().x1) {
      this.speed.x2 = 0
      this.position.x2 = floor.pared.puntoInterno.x2 - this.height
    }
  }
}