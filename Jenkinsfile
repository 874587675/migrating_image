pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_DIR = '/home/ubuntu/dockercompose/all'
        COMPOSE_PRINT_FILE = 'docker-compose.print.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'GitHub 代码已自动拉取到工作空间'
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('Build JAR') {
            steps {
                echo '开始构建 JAR 包...'
                // 使用完整路径或确保 mvn 在 PATH 中
                sh 'mvn clean package -DskipTests'
                echo 'JAR 包构建完成'
            }
        }

        stage('Deploy Print') {
            steps {
                echo '开始部署 Print 服务...'
                sh '''
                    cd /opt/dockercompose/all
                    echo "停止旧容器..."
                    docker-compose -f compose.print.yml down
                    echo "删除旧镜像"
                    docker rmi print:1.0.0
                    echo "启动新容器..."
                    docker-compose -f compose.print.yml up -d
                    echo "部署完成"
                '''
            }
        }

        stage('Verify') {
            steps {
                echo '验证服务状态...'
                sh 'sleep 3'
                sh 'docker ps --filter "name=print"'
            }
        }
    }

    post {
        success {
            echo '构建部署成功！'
        }
        failure {
            echo '构建部署失败，请检查日志'
        }
    }
}