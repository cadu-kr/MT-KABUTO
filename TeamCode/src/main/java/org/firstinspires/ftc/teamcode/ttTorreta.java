package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "TT-Torreta", group = "TESTE")
public class ttTorreta extends LinearOpMode {


    // MENSAGEM EXTREMAMENTE IMPORTANTE !!!!!
    // CODIGO FEITO INTERAMENTE COM IA(Claudinho) POIS SEM MOTOR, E SÓ COM UM SERVO DE GIRO CONTINUO NÃO PODIA FAZER MUITA COISA, DAI TIVE QUE USAR OU FACAVA SEM TORRETA !!!

    CRServo torreta;
    Limelight3A limelight;
    IMU imu;

    // --- Ajustes ---
    double limitadorGiro = .05;
    double zonaMorta = 1.0;
    double GRAUS_POR_SEG = 300;      // velocidade da TORRETA a potência 1.0 (CALIBRAR!)
    double LIMITE_GRAUS = 300;       // ao passar disso, volta ao início
    double TOLERANCIA_HOME = 8;
    double POTENCIA_RETORNO = 0.5;
    double SUAVIZACAO = 0.2;         // 0 a 1: quanto a tag corrige a estimativa a cada ciclo

    // --- Estado ---
    double anguloEstimado = 0;       // 0 = posição inicial
    double offsetCalibrado = 0;
    boolean calibrado = false;
    boolean voltando = false;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        torreta = hardwareMap.get(CRServo.class, "torreta");

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,      // AJUSTE conforme a montagem do hub
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addLine("Torreta na posição inicial, vendo uma tag, e aperte A para calibrar.");
        telemetry.update();

        waitForStart();
        timer.reset();

        while (opModeIsActive()) {

            double dt = timer.seconds();
            timer.reset();

            LLResult result = limelight.getLatestResult();
            boolean temTag = result != null && result.isValid();
            double diferenca = 0;

            // --- Correção do ângulo pela AprilTag ---
            if (temTag) {
                double yawCamera = result.getBotpose().getOrientation().getYaw(AngleUnit.DEGREES);
                double yawRobo = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                diferenca = yawCamera - yawRobo;

                // Calibração: torreta no zero + vendo a tag + aperta A
                if (gamepad1.left_bumper) {
                    offsetCalibrado = diferenca;
                    calibrado = true;
                    anguloEstimado = 0;
                    voltando = false;
                }

                if (calibrado) {
                    double medido = normalizar(diferenca - offsetCalibrado);   // -180 a 180
                    double voltas = Math.round((anguloEstimado - medido) / 360.0);
                    double anguloReal = medido + 360.0 * voltas;
                    anguloEstimado += SUAVIZACAO * (anguloReal - anguloEstimado);
                }
            }

            // Zera manualmente a estimativa (torreta no início, sem tag)
            if (gamepad1.b) {
                anguloEstimado = 0;
                voltando = false;
            }

            // --- Controle da torreta ---
            double power;

            if (voltando) {
                if (Math.abs(anguloEstimado) <= TOLERANCIA_HOME) {
                    voltando = false;
                    power = 0;
                } else {
                    power = -Math.signum(anguloEstimado) * POTENCIA_RETORNO;
                }
            } else {
                if (temTag) {
                    double eixoTx = result.getTx();
                    power = Range.clip(eixoTx * limitadorGiro, -1, 1);
                    if (Math.abs(eixoTx) < zonaMorta) power = 0;
                } else {
                    power = gamepad1.left_stick_x;
                }

                if (Math.abs(anguloEstimado) >= LIMITE_GRAUS) {
                    voltando = true;
                }
            }

            torreta.setPower(power);

            // Integra o movimento estimado
            anguloEstimado += power * GRAUS_POR_SEG * dt;

            telemetry.addData("Tag visível", temTag);
            telemetry.addData("Calibrado", calibrado);
            telemetry.addData("Diferença cam-robô", "%.1f", diferenca);
            telemetry.addData("Angulo estimado", "%.1f", anguloEstimado);
            telemetry.addData("Voltando", voltando);
            telemetry.update();
        }
        limelight.stop();
    }

    private double normalizar(double graus) {
        while (graus > 180) graus -= 360;
        while (graus < -180) graus += 360;
        return graus;
    }
}