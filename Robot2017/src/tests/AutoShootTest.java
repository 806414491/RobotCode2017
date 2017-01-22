package tests;

import org.junit.Test;
import org.usfirst.frc199.Robot2017.commands.AutoShoot;
import org.usfirst.frc199.Robot2017.subsystems.ShooterInterface;

import static org.mockito.Mockito.*;

public class AutoShootTest {
	
	@Test
	public void test(){
		ShooterInterface mockShooter = mock(ShooterInterface.class);
		
		AutoShoot testCommand = new AutoShoot(0, 0, mockShooter);
		
		testCommand.initialize();
		verify(mockShooter).setShooterPIDTarget(0);
		
		testCommand.execute();
		verify(mockShooter).updateShooterPID(0);
		verify(mockShooter).getShootEncoderRate();
		verify(mockShooter).shooterMotorStalled();
		verify(mockShooter).runShootMotor(0);
		verify(mockShooter).getShooterPIDOutput();
		verify(mockShooter).getShootEncoderRate();
		verify(mockShooter).runFeederMotor(1);
		
		testCommand.end();
		verify(mockShooter).stopShootMotor();
	}
}
