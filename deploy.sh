#!/bin/bash

# 项目编译打包部署脚本
# 作者: Kurisu
# 日期: $(date +%Y-%m-%d)

set -e  # 遇到错误时终止脚本

jdk_path=$(/usr/libexec/java_home -v 17)

# 配置变量
PROJECT_NAME="kotlin-gradle"
VERSION="1.0.0"
IMAGE_NAME="${PROJECT_NAME}:${VERSION}"
CONTAINER_NAME="${PROJECT_NAME}-app"

# 颜色输出定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 打印信息函数
echo_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

echo_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

echo_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查命令是否存在
check_command() {
    if ! command -v $1 &> /dev/null
    then
        echo_error "$1 could not be found"
        exit 1
    fi
}

  if [ -n $jdk_path ]; then
    export JAVA_HOME=$jdk_path
  fi

# 清理旧构建
clean() {
    echo_info "Cleaning old builds..."
    ./gradlew clean
}

# 编译和打包
build() {
    echo_info "Building project with Gradle..."
    ./gradlew build -x test  # 跳过测试以加快构建，如需运行测试请移除-x test
}

# 构建Docker镜像
build_docker() {
    echo_info "Building Docker image..."
    docker build -t ${IMAGE_NAME} .
}

# 停止并删除旧容器
remove_old_container() {
    echo_info "Stopping and removing old container..."
    if [ $(docker ps -aq -f name=${CONTAINER_NAME}) ]; then
        docker stop ${CONTAINER_NAME} 2>/dev/null || true
        docker rm ${CONTAINER_NAME} 2>/dev/null || true
    fi
}

# 运行新容器
run_container() {
    echo_info "Running new container..."
    docker run -d \
        --name ${CONTAINER_NAME} \
        -p 9990:8080 \
        ${IMAGE_NAME}
}

# 推送镜像到仓库（可选）
push_image() {
    # 这里可以添加推送镜像到Docker Hub或私有仓库的命令
    # 例如: docker tag ${IMAGE_NAME} your-dockerhub-username/${IMAGE_NAME}
    #      docker push your-dockerhub-username/${IMAGE_NAME}
    echo_warn "Push image functionality not implemented. Add your registry details to push."
}

# 主部署流程
main() {
    echo_info "Starting deployment process for ${PROJECT_NAME} v${VERSION}"

    # 检查必要命令
    check_command "gradle"
    check_command "docker"

    # 确保在项目根目录
    if [ ! -f "build.gradle.kts" ]; then
        echo_error "Please run this script from the project root directory"
        exit 1
    fi

    # 执行部署步骤
    clean
    build
    build_docker
    remove_old_container
    run_container

    echo_info "Deployment completed successfully!"
    echo_info "Application is running in container: ${CONTAINER_NAME}"
    echo_info "Access the application at: http://localhost:9990"
}

# 运行主函数
main "$@"