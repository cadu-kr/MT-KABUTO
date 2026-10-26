package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter{

    DcMotor shooter;
    double power = .6;
    boolean addPotencia = false;
    boolean removePotencia = false;

    public void inicio(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
    }
    public void rodar(Gamepad gamepad1){
        if (gamepad1.dpad_up && !addPotencia) {
            power+=.1;
        }
        else if (gamepad1.dpad_down && !removePotencia) {
            power-=.1;
        }
        addPotencia = gamepad1.dpad_up;
        removePotencia = gamepad1.dpad_down;

        if (power > 1) {
            power = 1;
        }
        else if (power < -1) {
            power = -1;
        }

        if (gamepad1.right_trigger >= .2) {
            shooter.setPower(power);
        }
        else {
            shooter.setPower(0);
        }
    }
    public void telemetry(Telemetry telemetry){
        telemetry.addData("Potência: ", power);
    }
}
