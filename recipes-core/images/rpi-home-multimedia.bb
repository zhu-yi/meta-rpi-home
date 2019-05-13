# Copyright (C) 2019 Zhu Yi <dev.yi.zhu@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A multimedia image for rpi-home device."
LICENSE = "MIT"

require rpi-home-base.bb

IMAGE_INSTALL += " \
    opencv \
    ffmpeg \
"

IMAGE_INSTALL += "packagegroup-rpi-home-test"
