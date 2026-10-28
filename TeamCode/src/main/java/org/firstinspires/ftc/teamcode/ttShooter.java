package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp(name = "Shooter", group = "TESTE")
public class ttShooter extends OpMode {
    DcMotor shooter;
    Servo compressaoShooter;
    ColorSensor sensor;
    Limelight3A limelight;
    ElapsedTime espera = new ElapsedTime();
    //--------------------------------
    double potencia = .55;
    boolean addPotencia = false;
    boolean removePotencia = false;
    //---------------------------------
    static final int limiteAzul = 215;
    static final int limiteVermelho = 280;
    boolean maiorBlue = false;
    boolean maiorRed = false;
    //-----------------------------------
    double[] distanciasAuto = {10, 20, 30, 40, 50}; //---> Definir as distancias para atirar
    double[] potenciasAuto = {.55, .6, .7, .8, .9}; //---> Definir as potencias de acordo com as distancias
    double potenciaCerta;
    boolean tagVisivel = false;


    public void colorSensor() {
        int red = sensor.red();
        int blue = sensor.blue();

        if (blue < limiteAzul) {
            blue = 0;
        }
        if (red < limiteVermelho) {
            red = 0;
        }

        maiorBlue = blue > red;
        maiorRed = red > blue;

        if (maiorBlue) {
            telemetry.addData("Detectado", "Nectar");
        } else if (maiorRed) {
            telemetry.addData("Detectado", "Polen");
        } else {
            telemetry.addData("Detectado", "Nada");
        }
    }

    public void distanciaLimelight() {
        double distanciaTag = -1;
        tagVisivel = false;
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();

            if (!tags.isEmpty()) {
                Pose3D p = tags.get(0).getTargetPoseCameraSpace();
                double x = p.getPosition().x;
                double y = p.getPosition().y;
                double z = p.getPosition().z;
                distanciaTag = Math.sqrt(x*x + y*y + z*z);
                tagVisivel = true;
            }
        }
        if (tagVisivel) {
            int melhorPotencia = 0;
            double menorDiferenca = Double.MAX_VALUE;

            for (int i=0; i < distanciasAuto.length; i++) {
                double diferenca = Math.abs(distanciasAuto[i] - distanciaTag);

                if (diferenca < menorDiferenca) {
                    menorDiferenca = diferenca;
                    melhorPotencia = i;
                }
                potenciaCerta = potenciasAuto[melhorPotencia];
            }
        }
    }

    public void compressao() {
        if (maiorBlue) {
            espera.reset();
            compressaoShooter.setPosition(0);
            if (espera.milliseconds() >= 400) {
                compressaoShooter.setPosition(.6);
            }
        }
    }

    public void potencia() {
        if (gamepad1.dpad_up && !addPotencia) {
            potencia+= .05;
        }
        else if (gamepad1.dpad_down && !removePotencia) {
            potencia-= .05;
        }
        potencia = Math.max(0, Math.min(1, potencia));
        addPotencia = gamepad1.dpad_up;
        removePotencia = gamepad1.dpad_down;

        telemetry.addData("Potencia", potencia);
    }

    public void lancamento() {
        if (tagVisivel && gamepad1.left_trigger >= .2) {
            shooter.setPower(potenciaCerta);
        }
        else {
            if (gamepad1.right_trigger >= .2) {
                shooter.setPower(potencia);
            }
            else {
                shooter.setPower(0);
            }
        }
    }

    @Override
    public void init() {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        compressaoShooter = hardwareMap.get(Servo.class, "compressao");
        compressaoShooter.setPosition(.6);

        sensor = hardwareMap.get(ColorSensor.class, "sensor-color");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void loop() {
        colorSensor();
        compressao();
        //distanciaLimelight();
        potencia();
        lancamento();
        telemetry.update();
    }
}
