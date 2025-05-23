.SUFFIXES : .c .cpp .o

.cpp.o:
	$(CCC) $(CCFLAGS) -c $(IPATHS) $<

.c.o:
	$(CC) $(CFLAGS) -c $(IPATHS) $<

.y.c:

# Platform specific definitions for Linux/GNU. Assumes 
# GNU GCC 3.x or 2.95.x compiler.

# The following variables are platform specific and may need
# to be edited if moving to a different system.

# compiler defs
CC        = gcc
CCC       = g++
CVARS_    = -DASN1RT -DGNU -D_GNU_SOURCE -DHAVE_STDINT_H -DHAVE_VA_COPY -fPIC
LINKDBG_  = -g
CELF_     = -fPIC
CUSEELF_  = -fpic
CEXCEPT_  = -fexceptions
CVARS0_   = $(CVARS_)
CVARSR_   = $(CVARS_)
CVARSMT_  = $(CVARS_)
CVARSMTR_ = $(CVARS_)
CVARSMTD_ = $(CVARS_)
CVARSMTR_ = $(CVARS_)
CDEV_     = -D_TRACE
COPTIMIZE0_ = -O2
GCCWARN_ = -Wall -Wpointer-arith -Wextra -Wundef -Wno-unused-parameter -Wshadow -Wcast-align -Wcomments -Wredundant-decls
WERROR = -Werror
BLDSUBDIR  = release
CFLAGS_    = $(GCCWARN_) -Wnested-externs
CCFLAGS_   = $(GCCWARN_) -Wno-reorder $(CEXCEPT_)
COPTIMIZE_ = $(COPTIMIZE0_) -D_OPTIMIZED  
CDEBUG_    = -g
LINKOPT_   = -o $@ -static
CBLDTYPE_  = $(COPTIMIZE_)


CCDEBUG_  = $(CDEBUG_)

LIBCMD    = ar r $@
LIBADD    = $(LIBCMD)
LINK      = $(CCC)
LINKSO    = $(LINK)
LINKOPT2  = $(LINKOPT_)
LINKELF_  = -shared $(CELF_) -o $@
LINKELF2_ = $(LINKELF_)
LINKUELF_ = $(CUSEELF_) -o $@
COMPACT   = -Os -D_COMPACT
OBJOUT    = -o $@

# File extensions
EXE     = 
OBJ     = .o
SO      = .so

# Run-time library
LIBPFX  = lib
LIBEXT  = a
LPPFX   = -L
LLPFX   = -l
LLEXT   =
LLAEXT  = 

A       = .$(LIBEXT)
MTA     = $(A)
MDA     = $(A)

# Include and library paths
PS      = /
FS      = :
IPATHS_ = 

# O/S commands
COPY     = cp -f
MOVE     = mv -f
MV       = $(MOVE)
RM       = rm -f
STRIP    = strip
MAKE     = make
RMDIR    = rm -rf
MKDIR    = mkdir -p

LLSYS   = -lm -lpthread

# LIBXML2 defs
LIBXML2ROOT = $(OSROOTDIR)/libxml2src
LIBXML2INC  = $(LIBXML2ROOT)/include
LIBXML2LIBDIR = $(OSROOTDIR)/c/lib
LIBXML2NAME = libxml2.a
LIBXML2LINK = -lxml2

# RLM defs
RLMDIR = $(OSROOTDIR)/licmgr/RLM
RLMLIBDIR = $(RLMDIR)/bin

# Directories
CDIR    = c
CPPDIR  = cpp

# START ASN1C
# Link libraries
LLBER   = -lasn1ber
LLJSON  = -lasn1json
LLMDER   = -lasn1mder
LLOER   = -lasn1oer
LLPER   = -lasn1per
LLXER   = -lasn1xer
LLXML   = -lasn1xml
LLRT    = -lasn1rt
LLRLM   = -lrlm
LLLIC   = -llicense
LLASN1C = -lasn1c
LLOSCOM = -loscom
LLX2A   = -lxsd2asn1
LLX2AAC = -lxsd2asn1ac
RTLIBNAME =  libasn1rt.a
BERLIBNAME = libasn1ber.a
JSONLIBNAME = libasn1json.a
PERLIBNAME = libasn1per.a
XERLIBNAME = libasn1xer.a
XMLLIBNAME = libasn1xml.a
RLMLIBNAME = librlm.a
LICLIBNAME = liblicense.a
OSLIBNAME  = liboscom.a
X2ALIBNAME = libxsd2asn1.a
X2AACLIBNAME = libxsd2asn1ac.a
# END ASN1C

# START XBINDER
# XBinder specific platform definitions
# END XBINDER
